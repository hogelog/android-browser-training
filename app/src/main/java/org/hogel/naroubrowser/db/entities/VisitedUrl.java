package org.hogel.naroubrowser.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(suppressConstructorProperties = true)
public class VisitedUrl {
    String url;

    String title;
}
