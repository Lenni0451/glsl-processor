package io.github.ocelot.glslprocessor.api.node;

import io.github.ocelot.glslprocessor.api.GlslInjectionPoint;
import io.github.ocelot.glslprocessor.api.node.function.GlslFunctionNode;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Ocelot
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public class GlslNodeList implements List<GlslNode> {

    @Delegate
    private final List<GlslNode> nodes;

    public GlslNodeList() {
        this.nodes = new ArrayList<>();
    }

    public GlslNodeList(final Collection<GlslNode> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }

    public void add(final GlslInjectionPoint point, final GlslNode element) {
        this.nodes.add(this.getInjectionIndex(point), element);
    }

    public void addAll(final GlslInjectionPoint point, final Collection<GlslNode> nodes) {
        this.nodes.addAll(this.getInjectionIndex(point), nodes);
    }

    private int getInjectionIndex(final GlslInjectionPoint point) {
        switch (point) {
            case BEFORE_DECLARATIONS -> {
                for (int i = 0; i < this.nodes.size(); i++) {
                    if (!(this.nodes.get(i) instanceof GlslFunctionNode)) {
                        return i;
                    }
                }
            }
            case AFTER_DECLARATIONS -> {
                for (int i = 0; i < this.nodes.size(); i++) {
                    if (this.nodes.get(i) instanceof GlslFunctionNode) {
                        return i;
                    }
                }
            }
            case BEFORE_MAIN -> {
                for (int i = 0; i < this.nodes.size(); i++) {
                    if (this.nodes.get(i) instanceof GlslFunctionNode functionNode && "main".equals(functionNode.getHeader().getName())) {
                        return i;
                    }
                }
            }
            case AFTER_MAIN -> {
                for (int i = 0; i < this.nodes.size(); i++) {
                    if (this.nodes.get(i) instanceof GlslFunctionNode functionNode && "main".equals(functionNode.getHeader().getName())) {
                        return i + 1;
                    }
                }
            }
            case BEFORE_FUNCTIONS -> {
                for (int i = 0; i < this.nodes.size(); i++) {
                    if (this.nodes.get(i) instanceof GlslFunctionNode) {
                        return i;
                    }
                }
            }
            case AFTER_FUNCTIONS -> {
                for (int i = this.nodes.size() - 1; i >= 0; i--) {
                    if (this.nodes.get(i) instanceof GlslFunctionNode) {
                        return i + 1;
                    }
                }
            }
        }
        return 0;
    }

}
