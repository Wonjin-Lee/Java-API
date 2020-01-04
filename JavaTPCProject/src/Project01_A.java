import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dto.BookDTO;

public class Project01_A {
	// Object(BookDTO) -> JSON(String)
	public static void main(String[] args) {
		BookDTO dto = new BookDTO("자바", 21000, "에이콘", 670);
		Gson g = new Gson();
		
		// Object(BookDTO) -> JSON(String)
		String json = g.toJson(dto);
		System.out.println(json);
		
		// JSON(String) -> Object(BookDTO)
		BookDTO new_dto = g.fromJson(json, BookDTO.class);
		System.out.println(new_dto);
		
		
		// Object(List<BookDTO>) -> JSON(String) : [{ }, { } ... ]
		List<BookDTO> list = new ArrayList<BookDTO>();
		list.add(new BookDTO("자바", 21000, "에이콘", 670));
		list.add(new BookDTO("파이썬", 32000, "한빛", 520));
		list.add(new BookDTO("루비", 38000, "인사이트", 480));

		String listJson = g.toJson(list);
		System.out.println(listJson);
		
		// JSON(String) -> Object(List<BookDTO>)
		List<BookDTO> new_list = g.fromJson(listJson, new TypeToken<List<BookDTO>>() {}.getType());
		for (BookDTO bookDTO : new_list) {
			System.out.println(bookDTO);
		}
	}
}
