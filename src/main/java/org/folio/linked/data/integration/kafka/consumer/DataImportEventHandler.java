package org.folio.linked.data.integration.kafka.consumer;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.folio.linked.data.util.Constants.FOLIO_PROFILE;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.folio.linked.data.service.ResourceService;
import org.folio.marc4ld.service.marc2ld.Marc2BibframeMapper;
import org.folio.search.domain.dto.DataImportEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@Profile(FOLIO_PROFILE)
@RequiredArgsConstructor
public class DataImportEventHandler {

  private final Marc2BibframeMapper marc2BibframeMapper;
  private final ResourceService resourceService;

  public void handle(DataImportEvent event) {
    if (isNotEmpty(event.getMarcBib())) {
      var marc4ldResource = marc2BibframeMapper.fromMarcJson(event.getMarcBib());
      var id = resourceService.createResource(marc4ldResource);
      log.info("DataImportEvent with id [{}] was saved as LD resource with id [{}]", event.getId(), id);
    } else if (isNotEmpty(event.getMarcAuthority())) {
      // TODO - Process MARC authority record
      log.info("Processing MARC Authority record with event ID [{}]", event.getId());
    } else {
      log.error("DataImportEvent with id [{}], tenant [{}], eventType [{}] has no Marc record inside",
        event.getId(), event.getTenant(), event.getEventType());
    }
  }
}
