package com.jaketschwartz.javautils.manipulation;

import com.jaketschwartz.javautils.logging.Logger;
import com.jaketschwartz.javautils.logging.LoggerProvider;

import java.util.Optional;

/**
 * Manipulates objects at a class level.
 */
public class ObjectConversionUtil {
    private static final Logger log = LoggerProvider.logger(ObjectConversionUtil.class);

    /**
     * A safe way to cast an object to its superclass.
     * @param object The object to cast.
     * @param superClass The class that the object inherits.
     * @param <T> Any Type of object that's a child of U.
     * @param <U> Any Type of object that T is a child of.
     * @return The Optionally-wrapped cast object, or an empty Optional on null or bad input.
     */
    public static<T extends U, U> Optional<U> upcast(final T object, final Class<U> superClass) {
        return ObjectConversionUtil.cast(object, superClass);
    }

    /**
     * A safe way to cast an object to its subclass.
     * @param object The object to cast.
     * @param childClass The class that inherits the object.
     * @param <T> Any Type of object that is a superclass of U.
     * @param <U> Any Type of object that inherits T.
     * @return The Optionally-wrapped cast object, or an empty Optional on null or bad input.
     */
    public static<T, U extends T> Optional<U> downcast(final T object, final Class<U> childClass) {
        return ObjectConversionUtil.cast(object, childClass);
    }

    /**
     * A safe way to cast an object to another object type.
     * @param object The object to cast.
     * @param castClass Any other class that the object can be cast to.
     * @param <T> Any Type of object.
     * @param <U> Any other Type of object that T can be cast to.
     * @return The Optionally-warpped cast object, or an empty Optional on null or bad input.
     */
    public static<T, U> Optional<U> cast(final T object, final Class<U> castClass) {
        if (object == null || castClass == null) {
            log.warn("A null object [{}] or superclass was passed in!", object, castClass);
            return Optional.empty();
        }
        try {
            return Optional.of(castClass.cast(object));
        } catch (ClassCastException e) {
            log.error("Failed to cast a/an [{}] to a/an [{}]", object.getClass().getSimpleName(),
                    castClass.getSimpleName(), e);
            return Optional.empty();
        }
    }
}
