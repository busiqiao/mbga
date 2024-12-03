package cn.waitfor.mbga.dto.amqp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestMessage {
  private Long id;
  private String name;
}
