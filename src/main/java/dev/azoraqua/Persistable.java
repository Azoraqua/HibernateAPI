package dev.azoraqua;

import org.jetbrains.annotations.NotNull;

public interface Persistable<ID> {
    @NotNull
    ID getId();
}
