# IO.Swagger.Api.AuthenticationControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**LoginUser**](AuthenticationControllerApi.md#loginuser) | **POST** /api/v1/auth/login | 
[**RegisterUser**](AuthenticationControllerApi.md#registeruser) | **POST** /api/v1/auth/register | 
[**VerificaEmail**](AuthenticationControllerApi.md#verificaemail) | **GET** /api/v1/auth/verify/mail/{email} | 
[**VerificaUsername**](AuthenticationControllerApi.md#verificausername) | **GET** /api/v1/auth/verify/username/{username} | 

<a name="loginuser"></a>
# **LoginUser**
> string LoginUser (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class LoginUserExample
    {
        public void main()
        {
            var apiInstance = new AuthenticationControllerApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.LoginUser(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling AuthenticationControllerApi.LoginUser: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**string**](string.md)|  | 

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="registeruser"></a>
# **RegisterUser**
> string RegisterUser (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class RegisterUserExample
    {
        public void main()
        {
            var apiInstance = new AuthenticationControllerApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.RegisterUser(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling AuthenticationControllerApi.RegisterUser: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**string**](string.md)|  | 

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="verificaemail"></a>
# **VerificaEmail**
> bool? VerificaEmail (string email)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class VerificaEmailExample
    {
        public void main()
        {
            var apiInstance = new AuthenticationControllerApi();
            var email = email_example;  // string | 

            try
            {
                bool? result = apiInstance.VerificaEmail(email);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling AuthenticationControllerApi.VerificaEmail: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **email** | **string**|  | 

### Return type

**bool?**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="verificausername"></a>
# **VerificaUsername**
> bool? VerificaUsername (string username)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class VerificaUsernameExample
    {
        public void main()
        {
            var apiInstance = new AuthenticationControllerApi();
            var username = username_example;  // string | 

            try
            {
                bool? result = apiInstance.VerificaUsername(username);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling AuthenticationControllerApi.VerificaUsername: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **string**|  | 

### Return type

**bool?**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
