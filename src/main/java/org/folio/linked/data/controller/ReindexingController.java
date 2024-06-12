package org.folio.linked.data.controller;

import static org.folio.linked.data.util.Constants.FOLIO_PROFILE;
import static org.folio.linked.data.util.Constants.SEARCH_PROFILE;

import lombok.RequiredArgsConstructor;
import org.folio.linked.data.rest.resource.ReindexApi;
import org.folio.linked.data.service.ReindexService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Profile({FOLIO_PROFILE, SEARCH_PROFILE})
@RestController
@RequiredArgsConstructor
public class ReindexingController implements ReindexApi {

  private final ReindexService reIndexService;

  @Override
  public ResponseEntity<Void> reindex(String okapiTenant, Boolean full) {
    reIndexService.reindex(full);
    return ResponseEntity.noContent().build();
  }
}
