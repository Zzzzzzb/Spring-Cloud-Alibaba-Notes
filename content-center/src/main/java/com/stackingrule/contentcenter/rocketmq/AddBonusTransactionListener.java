package com.stackingrule.contentcenter.rocketmq;

import com.stackingrule.contentcenter.dao.messaging.RocketmqTransactionLogMapper;
import com.stackingrule.contentcenter.domain.dto.content.ShareAuditDTO;
import com.stackingrule.contentcenter.domain.entity.messaging.RocketmqTransactionLog;
import com.stackingrule.contentcenter.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@RocketMQTransactionListener(txProducerGroup = "tx-add-bonus-group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener {

    private final ShareService shareService;
    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        Integer shareId = Integer.valueOf((String) headers.get("share_id"));

        try {
            this.shareService.auditByIdWithRocketMqLog(shareId, (ShareAuditDTO) o, transactionId);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);

        // select * from xxx where transaction_id = xxx
        RocketmqTransactionLog transactionLog = this.rocketmqTransactionLogMapper.selectOne(
                RocketmqTransactionLog.builder()
                        .transactionId(transactionId)
                        .build()
        );

        if (transactionLog != null) {
            return RocketMQLocalTransactionState.COMMIT;
        }

        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
