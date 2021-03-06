package de.plushnikov.intellij.plugin;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.MavenDependencyUtil;
import org.jetbrains.annotations.NotNull;

public class LombokTestUtil {

  public static final DefaultLightProjectDescriptor LOMBOK_DESCRIPTOR = new DefaultLightProjectDescriptor() {

    @Override
    public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
      MavenDependencyUtil.addFromMaven(model, "org.projectlombok:lombok:1.18.12");
      MavenDependencyUtil.addFromMaven(model, "com.google.guava:guava:27.0.1-jre");
      model.getModuleExtension(LanguageLevelModuleExtension.class).setLanguageLevel(LanguageLevel.JDK_1_8);
    }
  };
}
