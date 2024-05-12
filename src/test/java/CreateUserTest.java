import org.junit.Assert;
import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.dto.ErrorMessageResponse;
import stellarburgers.staticmethodsandvariables.*;

public class CreateUserTest {
    UserAPI userAPI = new UserAPI();
    @Test
    public void doesCreatingNewUserWork(){
        String email = "sdhfhds@yandex.ru";
        String password = "ffhhhj";
        String name = "Ivan";
        CreateUserSuccessfulResponse response = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        Assert.assertTrue(response.isSuccess());


        userAPI.deleteUser(response.getAccessToken().replace("Bearer ",""));
    }
    @Test
    public void IsCreatingUserWhoAlreadyRegisteredImpossible(){
        String email = "sdhfhds@yandex.ru";
        String password = "ffhhhj";
        String name = "Ivan";
        CreateUserSuccessfulResponse response = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        ErrorMessageResponse response2 = userAPI.createUser(email,password,name).as(ErrorMessageResponse.class);
        Assert.assertFalse(response2.isSuccess());

        userAPI.deleteUser(response.getAccessToken().replace("Bearer ",""));
    }

    @Test
    public void IsCreatingUserWithoutEmailImpossible(){
        String email = null;
        String password = "ffhhhj";
        String name = "Ivan";
        ErrorMessageResponse response = userAPI.createUser(email,password,name).as(ErrorMessageResponse.class);
        Assert.assertFalse(response.isSuccess());
    }

    @Test
    public void IsCreatingUserWithoutPasswordImpossible(){
        String email = "sdhfhds@yandex.ru";
        String password = null;
        String name = "Ivan";
        ErrorMessageResponse response = userAPI.createUser(email,password,name).as(ErrorMessageResponse.class);
        Assert.assertFalse(response.isSuccess());
    }

    @Test
    public void IsCreatingUserWithoutNameImpossible(){
        String email = "sdhfhds@yandex.ru";
        String password = "ffhhhj";
        String name = null;
        ErrorMessageResponse response = userAPI.createUser(email,password,name).as(ErrorMessageResponse.class);
        Assert.assertFalse(response.isSuccess());
    }

}
