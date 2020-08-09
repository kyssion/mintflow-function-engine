package org.mintflow.reflection.util;

public enum TypeEnum {

    CHAR(char.class, Character.class, "CHAR"),
    BOOLEAN(boolean.class, Boolean.class, "BOOLEAN"),
    BYTE(byte.class, Byte.class, "BYTE"),
    DOUBLE(double.class, Double.class, "DOUBLE"),
    FLOAT(float.class, Double.class, "DOUBLE"),
    INT(int.class, Integer.class, "INT"),
    LONG(long.class, Long.class, "LONG"),
    SHORT(short.class, Short.class, "SHORT"),
    CHARS(char[].class, Character[].class, "CHARS"),
    BOOLEANS(boolean[].class, Boolean[].class, "BOOLEANS"),
    BYTES(byte[].class, Byte[].class, "BYTES"),
    DOUBLES(double[].class, Double[].class, "DOUBLES"),
    FLOATS(float[].class, Double[].class, "DOUBLES"),
    INTS(int[].class, Integer[].class, "INTS"),
    LONGS(long[].class, Long[].class, "LONGS"),
    SHORTS(short[].class, Short[].class, "SHORTS");

    private Class<?> type;
    private Class<?> objectType;
    private String name;

    TypeEnum(Class<?> type, Class<?> objectType, String name) {
        this.type = type;
        this.objectType = objectType;
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public Class<?> getObjectType() {
        return objectType;
    }

    public void setObjectType(Class<?> objectType) {
        this.objectType = objectType;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Class<?> getType(Class<?> type) {
        for (TypeEnum enumType : values()) {
            if (enumType.getType() == type) {
                return enumType.getObjectType();
            }
        }
        return type;
    }

    public static Class<?> getTypeByObjectType(Class<?> objectType) {
        for (TypeEnum enumType : values()) {
            if (enumType.getType() == objectType) {
                return enumType.getType();
            }
        }
        return objectType;
    }

}
