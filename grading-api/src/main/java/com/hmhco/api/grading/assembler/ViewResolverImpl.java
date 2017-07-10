package com.hmhco.api.grading.assembler;

import com.hmhco.api.grading.views.AbstractView;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

@Component
public class ViewResolverImpl implements ViewResolver {

    @Autowired
    private EntityAssemblerProvider entityAssemblerProvider;

    private ResourceAssembler<?, ?> emptyResourceAssembler = emptyResourceAssembler();

    @Override
    @SuppressWarnings("unchecked")
    public <V extends AbstractView, R extends Resource<V>> PagedResources<R> resolvePagedView(
            PagedResourcesAssembler<V> pagedResourcesAssembler, Page<V> page, String versionNbr) {

        if (CollectionUtils.isEmpty(page.getContent())) {
            return pagedResourcesAssembler.toResource(page, (ResourceAssembler<V, R>) emptyResourceAssembler);
        }

        Class<? extends AbstractView> clazz = page.getContent().get(0).getClass();
        ResourceAssembler<V, R> assembler = getAssembler(clazz);

        page.getContent().stream().forEach(view -> view.setVersion(versionNbr));
        return pagedResourcesAssembler.toResource(page, assembler);
    }

    @Override
    public <V extends AbstractView, R extends Resource<V>> R resolveSingleDtoView(V view, String versionNbr) {
        Class<? extends AbstractView> clazz = view.getClass();
        ResourceAssembler<V, R> assembler = getAssembler(clazz);

        view.setVersion(versionNbr);
        return assembler.toResource(view);
    }

    @Override
    public <V extends AbstractView, R extends Resource<V>> Resources<R> resolveResourcesView(Collection<V> views, String versionNbr) {

        if (CollectionUtils.isEmpty(views)) {
            return new Resources<>(Collections.emptyList());
        }

        Class<? extends AbstractView> clazz = views.iterator().next().getClass();
        ResourceAssembler<V, R> assembler = getAssembler(clazz);
        views.stream().forEach(view -> view.setVersion(versionNbr));
        return new Resources<>(views.stream().map(assembler::toResource).collect(Collectors.toList()));
    }

    @Override
    public <V extends AbstractView, R extends Resource<V>> List<R> resolveListView(Collection<V> views, String versionNbr) {

        if (CollectionUtils.isEmpty(views)) {
            return Collections.emptyList();
        }

        Class<? extends AbstractView> clazz = views.iterator().next().getClass();
        ResourceAssembler<V, R> assembler = getAssembler(clazz);
        views.stream().forEach(view -> view.setVersion(versionNbr));
        return views.stream().map(assembler::toResource).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private <V extends AbstractView, R extends Resource<V>> ResourceAssembler<V, R> getAssembler(Class<? extends AbstractView> clazz) {
        ResourceAssembler<V, R> assembler = (ResourceAssembler<V, R>) entityAssemblerProvider.getAssembler(clazz);

        if (assembler == null) {
            throw new IllegalArgumentException("no entity assembler configured for class " + clazz);
        }

        return assembler;
    }

    private ResourceAssembler<?, ?> emptyResourceAssembler() {
        return new ResourceAssembler<Object, Resource<?>>() {

            @Override
            public Resource<?> toResource(Object entity) {
                throw new UnsupportedOperationException("this assembler should not be used for non-empty collections");
            }
        };
    }
}
