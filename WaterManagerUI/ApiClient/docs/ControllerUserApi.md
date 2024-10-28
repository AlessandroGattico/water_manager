# IO.Swagger.Api.ControllerUserApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddUser**](ControllerUserApi.md#adduser) | **POST** /api/v1/user/add | 
[**DeleteUser**](ControllerUserApi.md#deleteuser) | **DELETE** /api/v1/user/delete | 
[**GetGestoreAzienda**](ControllerUserApi.md#getgestoreazienda) | **GET** /api/v1/user/get/ga/{id} | 
[**GetGestoreIdrico**](ControllerUserApi.md#getgestoreidrico) | **GET** /api/v1/user/get/gi/{id} | 

<a name="adduser"></a>
# **AddUser**
> int? AddUser (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddUserExample
    {
        public void main()
        {
            var apiInstance = new ControllerUserApi();
            var body = new string(); // string | 

            try
            {
                int? result = apiInstance.AddUser(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerUserApi.AddUser: " + e.Message );
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

**int?**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="deleteuser"></a>
# **DeleteUser**
> void DeleteUser (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteUserExample
    {
        public void main()
        {
            var apiInstance = new ControllerUserApi();
            var body = new string(); // string | 

            try
            {
                apiInstance.DeleteUser(body);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerUserApi.DeleteUser: " + e.Message );
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

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getgestoreazienda"></a>
# **GetGestoreAzienda**
> string GetGestoreAzienda (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetGestoreAziendaExample
    {
        public void main()
        {
            var apiInstance = new ControllerUserApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetGestoreAzienda(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerUserApi.GetGestoreAzienda: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int?**|  | 

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getgestoreidrico"></a>
# **GetGestoreIdrico**
> string GetGestoreIdrico (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetGestoreIdricoExample
    {
        public void main()
        {
            var apiInstance = new ControllerUserApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetGestoreIdrico(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerUserApi.GetGestoreIdrico: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int?**|  | 

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
