package payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultDTO implements Serializable {
	private String file_id;
	private String file_unique_id;
	private int file_size;
	private String file_path;

}