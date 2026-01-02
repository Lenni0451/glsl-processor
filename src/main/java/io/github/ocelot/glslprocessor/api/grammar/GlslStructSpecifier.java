package io.github.ocelot.glslprocessor.api.grammar;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Ocelot
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class GlslStructSpecifier implements GlslTypeSpecifier {

    private String name;
    private final List<GlslStructField> fields;

    GlslStructSpecifier(final String name, final Collection<GlslStructField> fields) {
        this(name, new ArrayList<>(fields));
    }

    private GlslStructSpecifier(final String name, final List<GlslStructField> fields) {
        this.name = name;
        this.fields = fields;
    }

    /**
     * Sets the fields in this struct.
     *
     * @param fields The new fields to use
     */
    public GlslStructSpecifier setFields(final GlslStructField... fields) {
        return this.setFields(Arrays.asList(fields));
    }

    /**
     * Sets the fields in this struct.
     *
     * @param fields The new fields to use
     */
    public GlslStructSpecifier setFields(final Collection<GlslStructField> fields) {
        this.fields.clear();
        this.fields.addAll(fields);
        return this;
    }

    /**
     * @return A deep copy of this struct
     */
    public GlslStructSpecifier copy() {
        List<GlslStructField> fields = new ArrayList<>(this.fields.size());
        for (GlslStructField field : this.fields) {
            fields.add(field.copy());
        }
        return new GlslStructSpecifier(this.name, fields);
    }

    @Override
    public boolean isStruct() {
        return true;
    }

}
