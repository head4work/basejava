package com.urise.webapp.model;

  public  class Section<T> {
      T content;

      public Section(T content) {
          this.content = content;
      }

      public T getContent() {
          return  content;
      }

 }
