# IO.Swagger.Api.ControllerAziendaApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddAzienda**](ControllerAziendaApi.md#addazienda) | **POST** /api/v1/azienda/add | 
[**DeleteAzienda**](ControllerAziendaApi.md#deleteazienda) | **DELETE** /api/v1/azienda/delete/{id} | 
[**GetAziendaGa**](ControllerAziendaApi.md#getaziendaga) | **GET** /api/v1/azienda/get/gestore/{id} | 
[**GetAziendaId**](ControllerAziendaApi.md#getaziendaid) | **GET** /api/v1/azienda/get/{id} | 

<a name="addazienda"></a>
# **AddAzienda**
> string AddAzienda (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddAziendaExample
    {
        public void main()
        {
            var apiInstance = new ControllerAziendaApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddAzienda(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAziendaApi.AddAzienda: " + e.Message );
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
<a name="deleteazienda"></a>
# **DeleteAzienda**
> string DeleteAzienda (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteAziendaExample
    {
        public void main()
        {
            var apiInstance = new ControllerAziendaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.DeleteAzienda(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAziendaApi.DeleteAzienda: " + e.Message );
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
<a name="getaziendaga"></a>
# **GetAziendaGa**
> string GetAziendaGa (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetAziendaGaExample
    {
        public void main()
        {
            var apiInstance = new ControllerAziendaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetAziendaGa(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAziendaApi.GetAziendaGa: " + e.Message );
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
<a name="getaziendaid"></a>
# **GetAziendaId**
> string GetAziendaId (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetAziendaIdExample
    {
        public void main()
        {
            var apiInstance = new ControllerAziendaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetAziendaId(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAziendaApi.GetAziendaId: " + e.Message );
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
