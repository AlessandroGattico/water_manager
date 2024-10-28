# IO.Swagger.Api.ControllerSensoreApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddSensore**](ControllerSensoreApi.md#addsensore) | **POST** /api/v1/azienda/sensore/add | 
[**DeleteSensore**](ControllerSensoreApi.md#deletesensore) | **DELETE** /api/v1/azienda/sensore/delete/{id} | 
[**GetSensoreId**](ControllerSensoreApi.md#getsensoreid) | **GET** /api/v1/azienda/sensore/get/{id} | 
[**GetSensoriCampo**](ControllerSensoreApi.md#getsensoricampo) | **GET** /api/v1/azienda/sensore/getCampo/{id} | 

<a name="addsensore"></a>
# **AddSensore**
> string AddSensore (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddSensoreExample
    {
        public void main()
        {
            var apiInstance = new ControllerSensoreApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddSensore(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerSensoreApi.AddSensore: " + e.Message );
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
<a name="deletesensore"></a>
# **DeleteSensore**
> string DeleteSensore (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteSensoreExample
    {
        public void main()
        {
            var apiInstance = new ControllerSensoreApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.DeleteSensore(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerSensoreApi.DeleteSensore: " + e.Message );
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
<a name="getsensoreid"></a>
# **GetSensoreId**
> string GetSensoreId (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetSensoreIdExample
    {
        public void main()
        {
            var apiInstance = new ControllerSensoreApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetSensoreId(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerSensoreApi.GetSensoreId: " + e.Message );
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
<a name="getsensoricampo"></a>
# **GetSensoriCampo**
> string GetSensoriCampo (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetSensoriCampoExample
    {
        public void main()
        {
            var apiInstance = new ControllerSensoreApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetSensoriCampo(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerSensoreApi.GetSensoriCampo: " + e.Message );
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
