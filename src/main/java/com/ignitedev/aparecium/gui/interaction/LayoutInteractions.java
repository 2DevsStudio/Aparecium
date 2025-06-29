/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.gui.interaction;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LayoutInteractions {

  @Builder.Default private boolean inventoryClose = true;

  @Builder.Default private boolean inventoryDrag = false;

  @Builder.Default private boolean inventoryMoveItem = false;

  @Builder.Default private boolean inventoryClick = false;
}
