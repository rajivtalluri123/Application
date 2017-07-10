package com.hmhco.api.grading.assembler;

import com.hmhco.api.grading.views.AbstractView;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

public interface ViewResolver {

    <V extends AbstractView, R extends Resource<V>> PagedResources<R> resolvePagedView(
        PagedResourcesAssembler<V> pagedResourcesAssembler, Page<V> page, String versionNbr);

    <V extends AbstractView, R extends Resource<V>> R resolveSingleDtoView(V view, String versionNbr);

    <V extends AbstractView, R extends Resource<V>> Resources<R> resolveResourcesView(Collection<V> views, String versionNbr);

    <V extends AbstractView, R extends Resource<V>> List<R> resolveListView(Collection<V> views, String versionNbr);
}