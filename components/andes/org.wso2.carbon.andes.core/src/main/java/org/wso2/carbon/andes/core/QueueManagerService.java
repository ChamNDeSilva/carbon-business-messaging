/*
 * Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 */

package org.wso2.carbon.andes.core;

import java.util.List;

/**
 * This interface provides the base for managing all queue related services
 */
public interface QueueManagerService {

    /**
     * Creates a new queue
     *
     * @param queueName new queue name
     * @throws QueueManagerException
     */
    public void createQueue(String queueName, boolean isExclusiveConsumerEnabled) throws QueueManagerException;

    /**
     * Gets a all the queues
     *
     * @return a list of queues
     * @throws QueueManagerException
     */
    public List<org.wso2.carbon.andes.core.types.Queue> getAllQueues() throws QueueManagerException;

    /**
     * This method is triggered when deleting a queue through management console.
     *
     * @param queueName name of the queue
     * @throws QueueManagerException
     */
    public void deleteQueue(String queueName) throws QueueManagerException;

    /**
     * This method is triggered unsubscribe from a durable subscription to delete topic related
     * entries from registry
     * @param topicName Topic name
     * @param subscriptionId  Subscription ID
     * @throws QueueManagerException
     */
    public void deleteTopicFromRegistry(String topicName, String subscriptionId) throws QueueManagerException;

    /**
     * Restore messages from the Dead Letter Queue to their original queues.
     *
     * @param messageIDs          Browser Message Id / External Message Id list
     * @param deadLetterQueueName Dead Letter Queue name for the respective tenant
     * @throws QueueManagerException
     */
    public void restoreMessagesFromDeadLetterQueue(String[] messageIDs, String deadLetterQueueName)
            throws QueueManagerException;

    /**
     * Restore messages from the Dead Letter Queue to another queue in the same tenant.
     *
     * @param messageIDs          Browser Message Id / External Message Id list
     * @param destination         The new destination queue for the messages in the same tenant
     * @param deadLetterQueueName Dead Letter Queue name for the respective tenant
     * @throws QueueManagerException
     */
    public void restoreMessagesFromDeadLetterQueueWithDifferentDestination(String[] messageIDs,
                                                                           String destination,
                                                                           String deadLetterQueueName)
            throws QueueManagerException;

    /**
     * Delete messages from the Dead Letter Queue and delete their content.
     *
     * @param messageIDs          Browser Message Id / External Message Id list to be deleted
     * @param deadLetterQueueName Dead Letter Queue name for the respective tenant
     * @throws QueueManagerException
     */
    public void deleteMessagesFromDeadLetterQueue(String[] messageIDs, String deadLetterQueueName)
            throws QueueManagerException;

    /**
     * Request broker to clean all messages not awaiting acknowledgement from the given queue.
     *
     * @param queueName the queue name
     * @throws QueueManagerException
     */
    public void purgeMessagesOfQueue(String queueName) throws QueueManagerException;

    /**
     * Gets the message count for a queue
     *
     * @param destinationName the destination name. the name of the queue or topic
     * @param msgPattern      The exchange type used to transfer messages with the given destinationName. e.g. "queue" or "topic"
     * @return the number of messages for a queue
     * @throws QueueManagerException
     */
    public long getMessageCount(String destinationName, String msgPattern)
            throws QueueManagerException;

    /**
     * Updates permission for a queue. Can be pub, consume, change permission and etc.
     *
     * @param queueName            the queue name
     * @param queueRolePermissions the new permissions for the queue
     * @throws QueueManagerException
     */
    public void updatePermission(String queueName,
                                 org.wso2.carbon.andes.core.types.QueueRolePermission[]
                                         queueRolePermissions)
            throws QueueManagerException;

    /**
     * Gets roles except for admin
     *
     * @return an array of roles
     * @throws QueueManagerException
     */
    public String[] getBackendRoles() throws QueueManagerException;

    /**
     * Gets role permissions assigned to a queue
     *
     * @param queueName the queue name
     * @return an array of queue role permissions
     * @throws QueueManagerException
     */
    public org.wso2.carbon.andes.core.types.QueueRolePermission[] getQueueRolePermission(
            String queueName) throws QueueManagerException;

    /**
     * Gets the messages of a queue
     *
     * @param nameOfQueue   name of the queue
     * @param userName      user name of the amqp url
     * @param accessKey     the access key of the amqp url
     * @param startingIndex the starting index of the messages
     * @param maxMsgCount   the maximum messages to return
     * @return an array of messages
     * @throws QueueManagerException
     */
    public org.wso2.carbon.andes.core.types.Message[] browseQueue(String nameOfQueue,
                                                                  String userName,
                                                                  String accessKey,
                                                                  int startingIndex,
                                                                  int maxMsgCount)
            throws QueueManagerException;

    /**
     * Gets total message count in a queue
     *
     * @param queueName the queue name
     * @return the number messages in a queue
     * @throws QueueManagerException
     */
    public long getTotalMessagesInQueue(String queueName) throws QueueManagerException;

    /**
     * Send a message to the queue
     *
     * @param nameOfQueue      name of the queue
     * @param userName         the user name for the amqp url
     * @param accessKey        the access key for the amqp url
     * @param jmsType          the JMS type of the message
     * @param jmsCorrelationID the correlation ID of the JMS message
     * @param numberOfMessages number of messages to send
     * @param message          the message content/body
     * @param deliveryMode     the delivery mode
     * @param priority         priority of the message
     * @param expireTime       message expire time
     * @return true if message sent successfully, false otherwise.
     * @throws QueueManagerException
     */
    public boolean sendMessage(String nameOfQueue, String userName, String accessKey,
                               String jmsType,
                               String jmsCorrelationID, int numberOfMessages,
                               String message, int deliveryMode, int priority,
                               long expireTime) throws QueueManagerException;

    /**
     * Updating the ExclusiveConsumer value of the queue
     * @param queueName Name of the queue
     * @param isExclusiveConsumer exclusive consumer value of the queue
     * @throws QueueManagerException
     */
    public void updateExclusiveConsumerValue(String queueName, boolean isExclusiveConsumer)throws QueueManagerException;



}
