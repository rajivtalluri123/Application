package com.hmhco.api.grading.assembler;

import com.hmhco.api.grading.views.AbstractView;
import java.util.HashMap;
import java.util.Map;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

public class EntityAssemblerProvider {

    private Map<Class<? extends AbstractView>, ResourceAssembler<? extends AbstractView, ? extends Resource<?>>> assemblers = new HashMap<>();

    public ResourceAssembler<? extends AbstractView, ? extends Resource<?>> getAssembler(Class<?> clazz) {
        return assemblers.get(clazz);
    }

    public void addAssembler(Class<? extends AbstractView> clazz,
            ResourceAssembler<? extends AbstractView, ? extends Resource<?>> assembler) {
        assemblers.put(clazz, assembler);
    }
}
