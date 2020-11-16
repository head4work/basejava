package com.urise.webapp.model;

  public  class Section<T> {
      private final T content;

      public Section(T content) {
          this.content = content;
      }

      public T getContent() {
          return  content;
      }

 }
