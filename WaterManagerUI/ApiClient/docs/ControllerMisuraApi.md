# IO.Swagger.Api.ControllerMisuraApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddMisura**](ControllerMisuraApi.md#addmisura) | **POST** /api/v1/misura/add | 
[**DeleteMisura**](ControllerMisuraApi.md#deletemisura) | **DELETE** /api/v1/misura/delete/{id} | 
[**GetMisuraId**](ControllerMisuraApi.md#getmisuraid) | **GET** /api/v1/misura/get/{id} | 
[**GetMisureSensore**](ControllerMisuraApi.md#getmisuresensore) | **GET** /api/v1/misura/get/all/{id} | 

<a name="addmisura"></a>
# **AddMisura**
> void AddMisura (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddMisuraExample
    {
        public void main()
        {
            var apiInstance = new ControllerMisuraApi();
            var body = new string(); // string | 

            try
            {
                apiInstance.AddMisura(body);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerMisuraApi.AddMisura: " + e.Message );
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
<a name="deletemisura"></a>
# **DeleteMisura**
> string DeleteMisura (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteMisuraExample
    {
        public void main()
        {
            var apiInstance = new ControllerMisuraApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.DeleteMisura(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerMisuraApi.DeleteMisura: " + e.Message );
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
<a name="getmisuraid"></a>
# **GetMisuraId**
> string GetMisuraId (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetMisuraIdExample
    {
        public void main()
        {
            var apiInstance = new ControllerMisuraApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetMisuraId(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerMisuraApi.GetMisuraId: " + e.Message );
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
<a name="getmisuresensore"></a>
# **GetMisureSensore**
> string GetMisureSensore (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetMisureSensoreExample
    {
        public void main()
        {
            var apiInstance = new ControllerMisuraApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetMisureSensore(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerMisuraApi.GetMisureSensore: " + e.Message );
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
