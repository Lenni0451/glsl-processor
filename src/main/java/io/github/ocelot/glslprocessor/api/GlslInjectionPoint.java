package io.github.ocelot.glslprocessor.api;

import io.github.ocelot.glslprocessor.api.node.GlslTree;

/**
 * Reference locations inside a {@link GlslTree} nodes can be added to.
 *
 * @author Ocelot
 * @since 1.0.0
 */
public enum GlslInjectionPoint {

    BEFORE_DECLARATIONS,
    AFTER_DECLARATIONS,
    BEFORE_FUNCTIONS,
    AFTER_FUNCTIONS,
    BEFORE_MAIN,
    AFTER_MAIN,

}
