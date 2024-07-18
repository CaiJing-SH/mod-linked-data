package org.folio.linked.data.validation.entity;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.folio.ld.dictionary.PropertyDictionary.MAIN_TITLE;
import static org.folio.ld.dictionary.ResourceTypeDictionary.INSTANCE;
import static org.folio.ld.dictionary.ResourceTypeDictionary.TITLE;
import static org.folio.ld.dictionary.ResourceTypeDictionary.WORK;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.folio.linked.data.model.entity.Resource;
import org.folio.linked.data.model.entity.ResourceEdge;
import org.folio.linked.data.validation.PrimaryTitleConstraint;

public class PrimaryTitleEntityValidator implements ConstraintValidator<PrimaryTitleConstraint, Resource> {

  @Override
  public boolean isValid(Resource resource, ConstraintValidatorContext context) {
    if (resource.isNotOfType(INSTANCE) && resource.isNotOfType(WORK)) {
      return true;
    }
    if (isEmpty(resource.getOutgoingEdges())) {
      return false;
    }
    return resource.getOutgoingEdges().stream()
      .map(ResourceEdge::getTarget)
      .filter(target -> target.isOfType(TITLE) && nonNull(target.getDoc()))
      .map(Resource::getDoc)
      .anyMatch(this::containsMainTitle);
  }

  private boolean containsMainTitle(JsonNode doc) {
    return doc.has(MAIN_TITLE.getValue()) && !doc.get(MAIN_TITLE.getValue()).isEmpty();
  }
}
