package com.geetanjali.twtdw.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class Representation
{
        private List<String> statuses;

        public Representation() {
        }

        public Representation(List<String> statuses) {
            this.statuses = statuses;
        }

        @JsonProperty
        public List<String> getContent() {
            return statuses;
        }

        @Override
        public String toString()
        {
            return null;
        }
}

