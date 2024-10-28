# IO.Swagger.Api.ControllerColtivazioneApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddColtivazione**](ControllerColtivazioneApi.md#addcoltivazione) | **POST** /api/v1/azienda/coltivazione/add | 
[**DeleteColtivazione**](ControllerColtivazioneApi.md#deletecoltivazione) | **DELETE** /api/v1/azienda/coltivazione/delete/{id} | 
[**GetColtivazioneId**](ControllerColtivazioneApi.md#getcoltivazioneid) | **GET** /api/v1/azienda/coltivazione/get/{id} | 
[**GetColtivazioniCampo**](ControllerColtivazioneApi.md#getcoltivazionicampo) | **GET** /api/v1/azienda/coltivazione/get/all/{id} | 

<a name="addcoltivazione"></a>
# **AddColtivazione**
> string AddColtivazione (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddColtivazioneExample
    {
        public void main()
        {
            var apiInstance = new ControllerColtivazioneApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddColtivazione(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerColtivazioneApi.AddColtivazione: " + e.Message );
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
<a name="deletecoltivazione"></a>
# **DeleteColtivazione**
> string DeleteColtivazione (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteColtivazioneExample
    {
        public void main()
        {
            var apiInstance = new ControllerColtivazioneApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.DeleteColtivazione(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerColtivazioneApi.DeleteColtivazione: " + e.Message );
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
<a name="getcoltivazioneid"></a>
# **GetColtivazioneId**
> string GetColtivazioneId (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetColtivazioneIdExample
    {
        public void main()
        {
            var apiInstance = new ControllerColtivazioneApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetColtivazioneId(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerColtivazioneApi.GetColtivazioneId: " + e.Message );
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
<a name="getcoltivazionicampo"></a>
# **GetColtivazioniCampo**
> string GetColtivazioniCampo (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetColtivazioniCampoExample
    {
        public void main()
        {
            var apiInstance = new ControllerColtivazioneApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetColtivazioniCampo(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerColtivazioneApi.GetColtivazioniCampo: " + e.Message );
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
