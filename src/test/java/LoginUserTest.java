import org.junit.Assert;
import org.junit.Test;
import stellarburgers.dto.CreateUserSuccessfulResponse;
import stellarburgers.dto.ErrorMessageResponse;
import stellarburgers.staticmethodsandvariables.UserAPI;

public class LoginUserTest {
    UserAPI userAPI = new UserAPI();
    @Test
    public void doesLoginUserWork(){
        String email = "sdhfhds@yandex.ru";
        String password = "ffhhhj";
        String name = "Ivan";
        userAPI.createUser(email,password,name);
        CreateUserSuccessfulResponse response = userAPI.loginUser(email,password).as(CreateUserSuccessfulResponse.class);
        Assert.assertTrue(response.isSuccess());

        userAPI.deleteUser(response.getAccessToken().replace("Bearer ",""));
    }

    @Test
    public void doesLoginUserWithWrongEmailNotWork(){
        String email = "sdhfhds@yandex.ru";
        String wrongEmail = "addfjgfjk@yandex.ru";
        String password = "ffhhhj";
        String name = "Ivan";
        CreateUserSuccessfulResponse response = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        ErrorMessageResponse response2 = userAPI.loginUser(wrongEmail,password).as(ErrorMessageResponse.class);
        Assert.assertFalse(response2.isSuccess());

        userAPI.deleteUser(response.getAccessToken().replace("Bearer ",""));
    }

    @Test
    public void doesLoginUserWithWrongPasswordNotWork(){
        String email = "sdhfhds@yandex.ru";
        String password = "ffhhhj";
        String wrongPassword = "adfgsrh";
        String name = "Ivan";
        CreateUserSuccessfulResponse response = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        ErrorMessageResponse response2 = userAPI.loginUser(email,wrongPassword).as(ErrorMessageResponse.class);
        Assert.assertFalse(response2.isSuccess());

        userAPI.deleteUser(response.getAccessToken().replace("Bearer ",""));
    }

    @Test
    public void doesLoginUserWithWrongEmailAndPasswordNotWork(){
        String email = "sdhfhds@yandex.ru";
        String wrongEmail = "addfjgfjk@yandex.ru";
        String password = "ffhhhj";
        String wrongPassword = "adfgsrh";
        String name = "Ivan";
        CreateUserSuccessfulResponse response = userAPI.createUser(email,password,name).as(CreateUserSuccessfulResponse.class);
        ErrorMessageResponse response2 = userAPI.loginUser(wrongEmail,wrongPassword).as(ErrorMessageResponse.class);
        Assert.assertFalse(response2.isSuccess());

        userAPI.deleteUser(response.getAccessToken().replace("Bearer ",""));
    }


}
