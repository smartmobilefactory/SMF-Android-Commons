/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package de.smf.smftools.appcontainer.drawer;

/**
 * Interface used to communicate from the v21-specific code for configuring a DrawerLayout
 * to the DrawerLayout itself.
 */
interface DrawerLayoutImpl {
  void setChildInsets(Object insets, boolean drawStatusBar);
}
