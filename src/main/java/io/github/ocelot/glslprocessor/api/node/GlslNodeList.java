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

    public void add(final GlslInjectionPoint referencePoint, final GlslNode element) {
        this.nodes.add(this.getInjectionIndex(referencePoint), element);
    }

    public void addAll(final GlslInjectionPoint referencePoint, final Collection<GlslNode> nodes) {
        this.nodes.addAll(this.getInjectionIndex(referencePoint), nodes);
    }

    private int getInjectionIndex(final GlslInjectionPoint referencePoint) {
        switch (referencePoint) {
            case BEFORE_DECLARATIONS -> {
                for (int i = 0; i < this.nodes.size(); i++) {
                    GlslNode node = this.nodes.get(i);
                    if (this.isDeclarationNode(node) || this.isFunctionNode(node)) {
                        return i;
                    }
                }
            }
            case AFTER_DECLARATIONS -> {
                for (int i = 0; i < this.nodes.size(); i++) {
                    GlslNode node = this.nodes.get(i);
                    if (!this.isDeclarationNode(node)) {
                        return i;
                    }
                }
            }
            case BEFORE_FUNCTIONS -> {
                for (int i = 0; i < this.nodes.size(); i++) {
                    GlslNode node = this.nodes.get(i);
                    if (this.isFunctionNode(node)) {
                        return i;
                    }
                }
            }
            case AFTER_FUNCTIONS -> {
                for (int i = this.nodes.size() - 1; i >= 0; i--) {
                    GlslNode node = this.nodes.get(i);
                    if (this.isFunctionNode(node)) {
                        return i + 1;
                    }
                }
            }
            case BEFORE_MAIN -> {
                for (int i = 0; i < this.nodes.size(); i++) {
                    GlslNode node = this.nodes.get(i);
                    if (this.isMain(node)) {
                        return i;
                    }
                }
            }
            case AFTER_MAIN -> {
                for (int i = 0; i < this.nodes.size(); i++) {
                    GlslNode node = this.nodes.get(i);
                    if (this.isMain(node)) {
                        return i + 1;
                    }
                }
            }
        }
        return 0;
    }

    private boolean isDeclarationNode(final GlslNode node) {
        if (node instanceof GlslFunctionNode functionNode) {
            return functionNode.getBody() == null;
        }
        return true;
    }

    private boolean isFunctionNode(final GlslNode node) {
        return node instanceof GlslFunctionNode functionNode && functionNode.getBody() != null;
    }

    private boolean isMain(final GlslNode node) {
        return node instanceof GlslFunctionNode functionNode && "main".equals(functionNode.getName()) && functionNode.getBody() != null;
    }

}
