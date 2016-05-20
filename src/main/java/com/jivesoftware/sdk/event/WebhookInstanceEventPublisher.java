/*
 *
 *  * Copyright 2013 Jive Software
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *  
 */

package com.jivesoftware.sdk.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import java.util.List;

/**
 * Created by rrutan on 8/14/14.
 */
@Component
@Singleton
public class WebhookInstanceEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(WebhookInstanceEventPublisher.class);

    private List<WebhookInstanceEventListener> WebhookInstanceEventListeners;

    public List<WebhookInstanceEventListener> getWebhookInstanceEventListeners() { return WebhookInstanceEventListeners; }
    public void setWebhookInstanceEventListeners(List<WebhookInstanceEventListener> WebhookInstanceEventListeners) {
        this.WebhookInstanceEventListeners = WebhookInstanceEventListeners;
    } // end get/setOauthEventListeners


    public void publishEvent(WebhookInstanceEvent event) {
        if (WebhookInstanceEventListeners != null ) {
            for (WebhookInstanceEventListener listener : WebhookInstanceEventListeners) {
                if (log.isTraceEnabled()) { log.trace("["+ listener.getClass().getSimpleName()+"]..."); }
                if (listener.accepts(event)) {
                    try {
                    if (log.isTraceEnabled()) { log.trace("[" + listener.getClass().getSimpleName() + "] accepted event[" + event.getType() + "]..."); }
                        listener.process(event);
                    } catch (WebhookInstanceEventListener.WebhookInstanceEventException tiee) {
                        //TODO: ADD FUNCTIONALITY INTO EXCEPTION TO CONTROL BREAKING LOOP
                        log.error("Unable to Process Tile Event, continuing to other listeners",tiee);
                    } // end try/catch
                } // end if
            } // end for
        } // end if

    } // end publishEvent

} // end class
