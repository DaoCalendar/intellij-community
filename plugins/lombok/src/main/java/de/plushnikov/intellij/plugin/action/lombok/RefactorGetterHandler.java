package de.plushnikov.intellij.plugin.action.lombok;

import com.intellij.codeInsight.generation.ClassMember;
import com.intellij.codeInsight.generation.EncapsulatableClassMember;
import com.intellij.codeInsight.generation.PsiElementClassMember;
import com.intellij.codeInsight.generation.PsiFieldMember;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PropertyUtil;
import de.plushnikov.intellij.plugin.LombokNames;
import de.plushnikov.intellij.plugin.action.BaseRefactorHandler;

import java.util.ArrayList;
import java.util.List;

public class RefactorGetterHandler extends BaseRefactorHandler {

  public RefactorGetterHandler(Project project, DataContext dataContext) {
    super(dataContext, project);
  }

  protected String getChooserTitle() {
    return "Select Fields to replace Getter-Method With @Getter";
  }

  @Override
  protected String getNothingFoundMessage() {
    return "No field getter have been found to generate @Getters for";
  }

  @Override
  protected String getNothingAcceptedMessage() {
    return "No fields with getter method were found";
  }

  @Override
  protected List<EncapsulatableClassMember> getEncapsulatableClassMembers(PsiClass psiClass) {
    final List<EncapsulatableClassMember> result = new ArrayList<>();
    for (PsiField field : psiClass.getFields()) {
      if (null != PropertyUtil.findPropertyGetter(psiClass, field.getName(), false, false)) {
        result.add(new PsiFieldMember(field));
      }
    }
    return result;
  }

  @Override
  protected void process(List<ClassMember> classMembers) {
    for (ClassMember classMember : classMembers) {
      final PsiElementClassMember elementClassMember = (PsiElementClassMember) classMember;

      PsiField psiField = (PsiField) elementClassMember.getPsiElement();
      PsiMethod psiMethod = PropertyUtil.findPropertyGetter(psiField.getContainingClass(), psiField.getName(), false, false);

      if (null != psiMethod) {
        PsiModifierList modifierList = psiField.getModifierList();
        if (null != modifierList) {
          PsiAnnotation psiAnnotation = modifierList.addAnnotation(LombokNames.GETTER);
//          psiAnnotation.setDeclaredAttributeValue("value", )

          psiMethod.delete();
        }
      }
    }
  }
}
