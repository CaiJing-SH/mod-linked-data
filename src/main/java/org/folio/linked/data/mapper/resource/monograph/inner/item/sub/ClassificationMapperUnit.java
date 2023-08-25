package org.folio.linked.data.mapper.resource.monograph.inner.item.sub;

import static org.folio.linked.data.util.BibframeConstants.CLASSIFICATION_PRED;
import static org.folio.linked.data.util.BibframeConstants.NOTE;

import lombok.RequiredArgsConstructor;
import org.folio.linked.data.domain.dto.Item2;
import org.folio.linked.data.domain.dto.ItemClassificationLcc2;
import org.folio.linked.data.mapper.resource.common.CoreMapper;
import org.folio.linked.data.mapper.resource.common.MapperUnit;
import org.folio.linked.data.model.entity.Resource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@MapperUnit(predicate = CLASSIFICATION_PRED)
public class ClassificationMapperUnit implements ItemSubResourceMapperUnit {

  private final CoreMapper coreMapper;

  @Override
  public Item2 toDto(Resource source, Item2 destination) {
    var classification = coreMapper.readResourceDoc(source, ItemClassificationLcc2.class);
    coreMapper.addMappedProperties(source, NOTE, classification::addNoteItem);
    return destination;
  }
}
