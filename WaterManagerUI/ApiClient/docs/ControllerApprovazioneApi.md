# IO.Swagger.Api.ControllerApprovazioneApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddApprovazione**](ControllerApprovazioneApi.md#addapprovazione) | **POST** /api/v1/bacino/approvazione/add | 
[**DeleteApprovazione**](ControllerApprovazioneApi.md#deleteapprovazione) | **DELETE** /api/v1/bacino/approvazione/delete/{id} | 
[**GetApprovazioneId**](ControllerApprovazioneApi.md#getapprovazioneid) | **GET** /api/v1/bacino/approvazione/get/{id} | 

<a name="addapprovazione"></a>
# **AddApprovazione**
> string AddApprovazione (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddApprovazioneExample
    {
        public void main()
        {
            var apiInstance = new ControllerApprovazioneApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddApprovazione(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerApprovazioneApi.AddApprovazione: " + e.Message );
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
<a name="deleteapprovazione"></a>
# **DeleteApprovazione**
> string DeleteApprovazione (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteApprovazioneExample
    {
        public void main()
        {
            var apiInstance = new ControllerApprovazioneApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.DeleteApprovazione(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerApprovazioneApi.DeleteApprovazione: " + e.Message );
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
<a name="getapprovazioneid"></a>
# **GetApprovazioneId**
> string GetApprovazioneId (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetApprovazioneIdExample
    {
        public void main()
        {
            var apiInstance = new ControllerApprovazioneApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetApprovazioneId(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerApprovazioneApi.GetApprovazioneId: " + e.Message );
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
