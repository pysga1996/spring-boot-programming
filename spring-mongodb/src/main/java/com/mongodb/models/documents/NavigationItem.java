package com.mongodb.models.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "navigation_item")
public class NavigationItem {

  @Id
  private String id;
  private String title;
  private String icon;
  private String tags;
  private String routerLink;
  private Boolean active;
  private Boolean open;
  private Collection<NavigationItem> items;
  private Boolean matched;
  private Boolean navTitle;
}
