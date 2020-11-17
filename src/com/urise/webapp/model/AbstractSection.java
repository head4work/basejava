package com.urise.webapp.model;

 abstract public class AbstractSection<T> {
     private final T content;

     public AbstractSection(T content) {
         this.content = content;
     }

     public T getContent() {
         return content;
     }

 }
