package junit;

import com.happyhouse.repository.PostRepository;
import org.junit.jupiter.api.Test;
import com.happyhouse.service.AddPostService;
import com.happyhouse.dto.AddPostRequest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class TestAddPost {
	
	@Mock
	private PostRepository postRepository;
	
	@InjectMocks
	private AddPostService addPostService;

    @Test
    void testValidInputsNoError() {
        AddPostRequest testPost = new AddPostRequest("oausfoiu aosidufpoia osaidufoiasd osaiduf ufidsaid", "this is the content", new ArrayList<String>(0));
        assertEquals(1, addPostService.checkValidInputs(testPost));
    }

	@Test
	void testValidInputsShortTitle() {
		AddPostRequest testPost = new AddPostRequest("", "this is the content", new ArrayList<String>(0));
		
		assertEquals(2, addPostService.checkValidInputs(testPost));
	}

    @Test
    void testValidInputsLongTitle() {
        AddPostRequest testPost = new AddPostRequest("oausfoiu aosidufpoia osaidufoiasd osaiduf ufidsaiduiaos", "this is the content", new ArrayList<String>(0));

        assertEquals(5, addPostService.checkValidInputs(testPost));
    }

    @Test
    void testValidInputsShortContent() {
        AddPostRequest testPost = new AddPostRequest("hi title", "", new ArrayList<String>(0));

        assertEquals(3, addPostService.checkValidInputs(testPost));
    }

    @Test
    void testValidInputsCombo() {
        AddPostRequest testPost = new AddPostRequest("", "", new ArrayList<String>(0));

        assertEquals(6, addPostService.checkValidInputs(testPost));
    }
}