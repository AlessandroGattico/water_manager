# IO.Swagger.Api.ControllerAttuatoreApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddAttuatore**](ControllerAttuatoreApi.md#addattuatore) | **POST** /api/v1/azienda/attuatore/add | 
[**DeleteAttuatore**](ControllerAttuatoreApi.md#deleteattuatore) | **DELETE** /api/v1/azienda/attuatore/delete/{id} | 
[**GetAttuaotriAttiviCampo**](ControllerAttuatoreApi.md#getattuaotriattivicampo) | **GET** /api/v1/azienda/attuatore/get/attivi/all/{id} | 
[**GetAttuatoreId**](ControllerAttuatoreApi.md#getattuatoreid) | **GET** /api/v1/azienda/attuatore/get/{id} | 
[**GetAttuatoriCampo**](ControllerAttuatoreApi.md#getattuatoricampo) | **GET** /api/v1/azienda/attuatore/getCampo/{id} | 

<a name="addattuatore"></a>
# **AddAttuatore**
> string AddAttuatore (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddAttuatoreExample
    {
        public void main()
        {
            var apiInstance = new ControllerAttuatoreApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddAttuatore(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAttuatoreApi.AddAttuatore: " + e.Message );
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
<a name="deleteattuatore"></a>
# **DeleteAttuatore**
> string DeleteAttuatore (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteAttuatoreExample
    {
        public void main()
        {
            var apiInstance = new ControllerAttuatoreApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.DeleteAttuatore(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAttuatoreApi.DeleteAttuatore: " + e.Message );
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
<a name="getattuaotriattivicampo"></a>
# **GetAttuaotriAttiviCampo**
> string GetAttuaotriAttiviCampo (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetAttuaotriAttiviCampoExample
    {
        public void main()
        {
            var apiInstance = new ControllerAttuatoreApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetAttuaotriAttiviCampo(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAttuatoreApi.GetAttuaotriAttiviCampo: " + e.Message );
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
<a name="getattuatoreid"></a>
# **GetAttuatoreId**
> string GetAttuatoreId (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetAttuatoreIdExample
    {
        public void main()
        {
            var apiInstance = new ControllerAttuatoreApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetAttuatoreId(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAttuatoreApi.GetAttuatoreId: " + e.Message );
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
<a name="getattuatoricampo"></a>
# **GetAttuatoriCampo**
> string GetAttuatoriCampo (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetAttuatoriCampoExample
    {
        public void main()
        {
            var apiInstance = new ControllerAttuatoreApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetAttuatoriCampo(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAttuatoreApi.GetAttuatoriCampo: " + e.Message );
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
