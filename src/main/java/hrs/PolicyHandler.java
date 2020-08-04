package hrs;

import hrs.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyHandler{

    @Autowired
    PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationCanceled_Paymentcancel(@Payload ReservationCanceled reservationCanceled){

        if(reservationCanceled.isMe()){
            System.out.println("##### listener Paymentcancel : " + reservationCanceled.toJson());

//            Optional<Payment> paymentOptional = paymentRepository.findById(reservationCanceled.getId());

            Payment payment = paymentRepository.findByReservationId(reservationCanceled.getId());

            if(payment != null){
                payment.setStatus("Payment Canceled");
                paymentRepository.save(payment);
            }
        }
    }
}
