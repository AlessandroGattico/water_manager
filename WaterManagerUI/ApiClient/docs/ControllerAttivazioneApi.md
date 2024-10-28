# IO.Swagger.Api.ControllerAttivazioneApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddAttivazione**](ControllerAttivazioneApi.md#addattivazione) | **POST** /api/v1/azienda/attivazione/add | 
[**DeleteAttivazione**](ControllerAttivazioneApi.md#deleteattivazione) | **DELETE** /api/v1/azienda/attivazione/delete/{id} | 
[**GetAttivazioneId**](ControllerAttivazioneApi.md#getattivazioneid) | **GET** /api/v1/azienda/attivazione/get/{id} | 
[**GetAttivazioniAttuatore**](ControllerAttivazioneApi.md#getattivazioniattuatore) | **GET** /api/v1/azienda/attivazione/getAttuatore/{id} | 

<a name="addattivazione"></a>
# **AddAttivazione**
> string AddAttivazione (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddAttivazioneExample
    {
        public void main()
        {
            var apiInstance = new ControllerAttivazioneApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddAttivazione(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAttivazioneApi.AddAttivazione: " + e.Message );
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
<a name="deleteattivazione"></a>
# **DeleteAttivazione**
> string DeleteAttivazione (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteAttivazioneExample
    {
        public void main()
        {
            var apiInstance = new ControllerAttivazioneApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.DeleteAttivazione(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAttivazioneApi.DeleteAttivazione: " + e.Message );
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
<a name="getattivazioneid"></a>
# **GetAttivazioneId**
> string GetAttivazioneId (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetAttivazioneIdExample
    {
        public void main()
        {
            var apiInstance = new ControllerAttivazioneApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetAttivazioneId(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAttivazioneApi.GetAttivazioneId: " + e.Message );
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
<a name="getattivazioniattuatore"></a>
# **GetAttivazioniAttuatore**
> string GetAttivazioniAttuatore (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetAttivazioniAttuatoreExample
    {
        public void main()
        {
            var apiInstance = new ControllerAttivazioneApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetAttivazioniAttuatore(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAttivazioneApi.GetAttivazioniAttuatore: " + e.Message );
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
