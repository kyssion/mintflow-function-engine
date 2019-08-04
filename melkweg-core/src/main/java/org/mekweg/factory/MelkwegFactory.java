package org.mekweg.factory;

import org.mekweg.Melkweg;

import java.util.concurrent.atomic.AtomicReference;


public interface MelkwegFactory {

    AtomicReference<Melkweg> finalMelweg =
            new AtomicReference<>();

    Melkweg create();

    default Melkweg getMelkweg() {
        return finalMelweg.get();
    }
}
