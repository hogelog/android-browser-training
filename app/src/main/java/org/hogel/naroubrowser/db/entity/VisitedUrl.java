package org.hogel.naroubrowser.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(suppressConstructorProperties = true)
public class VisitedUrl {
    String url;

    String title;
}
