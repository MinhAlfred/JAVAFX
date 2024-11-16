package org.example.hsf301.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HoverState {

    ENABLE(true),
    DISABLE(false);

    private final boolean status;

}
