package net.instantcom.mm7;

/**
 *
 * Created by bardug on 22/5/2017.
 */
public interface Contextual {

    /**
     * Context used for serializing/deserializing MM7 messages.
     *
     * @return context instance
     */
    MM7Context getContext();
}
