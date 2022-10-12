package aba.kover.core;

import aba.kover.core.detail.Detail;
import aba.kover.core.detail.KoverPosition;
import aba.kover.core.detail.KoverState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface KoverStateService {
    List<KoverState> addDetail(@NotNull KoverState currentState, Detail detail, KoverPosition position);
}
