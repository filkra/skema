package de.hhu.bsinfo.autochunk.demo;

import org.junit.BeforeClass;
import org.junit.Test;

import de.hhu.bsinfo.autochunk.demo.data.BoxedCollection;
import de.hhu.bsinfo.autochunk.demo.data.NestedObject;
import de.hhu.bsinfo.autochunk.demo.data.PrimitiveCollection;
import de.hhu.bsinfo.autochunk.demo.data.TestClass;
import de.hhu.bsinfo.autochunk.demo.schema.SchemaRegistry;
import de.hhu.bsinfo.autochunk.demo.util.ClassUtil;
import de.hhu.bsinfo.autochunk.demo.util.FieldUtil;
import de.hhu.bsinfo.autochunk.demo.util.Operation;
import de.hhu.bsinfo.autochunk.demo.util.UnsafeProvider;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PartialTest {

    @BeforeClass
    public static void setup() {
        SchemaRegistry.register(PrimitiveCollection.class);
        SchemaRegistry.register(BoxedCollection.class);
        SchemaRegistry.register(NestedObject.class);
        SchemaRegistry.register(TestClass.class);
    }

    @Test
    public void testNonNestedSerialize() {
        PrimitiveCollection object = new PrimitiveCollection();

        byte[] expected = SchemaSerializer.serialize(object);
        byte[] actual = new byte[expected.length];

        Operation operation = new Operation(object);

        for (int i = 0; i < expected.length; i++) {
            SchemaSerializer.serialize(operation, actual, i, 1);
        }

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testNestedSerialize() {
        BoxedCollection object = new BoxedCollection();

        byte[] expected = SchemaSerializer.serialize(object);
        byte[] actual = new byte[expected.length];

        Operation operation = new Operation(object);

        for (int i = 0; i < expected.length; i++) {
            SchemaSerializer.serialize(operation, actual, i, 1);
        }

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testNonNestedDeserialize() {
        PrimitiveCollection expected = new PrimitiveCollection();
        PrimitiveCollection actual = ClassUtil.allocateInstance(PrimitiveCollection.class);

        byte[] bytes = SchemaSerializer.serialize(expected);

        Operation operation = new Operation(actual);

        for (int i = 0; i < bytes.length; i++) {
            SchemaSerializer.deserialize(operation, bytes, i, 1);
        }

        assertEquals(expected, actual);
    }

    @Test
    public void testNestedDeserialize() {
        BoxedCollection expected = new BoxedCollection();
        BoxedCollection actual = ClassUtil.allocateInstance(BoxedCollection.class);

        byte[] bytes = SchemaSerializer.serialize(expected);

        Operation operation = new Operation(actual);

        for (int i = 0; i < bytes.length; i++) {
            SchemaSerializer.deserialize(operation, bytes, i, 1);
        }

        assertEquals(expected, actual);
    }
}
