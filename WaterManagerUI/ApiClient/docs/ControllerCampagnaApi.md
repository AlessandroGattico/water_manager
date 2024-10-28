# IO.Swagger.Api.ControllerCampagnaApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddCampagna**](ControllerCampagnaApi.md#addcampagna) | **POST** /api/v1/azienda/campagna/add | 
[**DeleteCampagna**](ControllerCampagnaApi.md#deletecampagna) | **DELETE** /api/v1/azienda/campagna/delete/{id} | 
[**GetCampagnaId**](ControllerCampagnaApi.md#getcampagnaid) | **GET** /api/v1/azienda/campagna/get/{id} | 
[**GetCampagneAzienda**](ControllerCampagnaApi.md#getcampagneazienda) | **GET** /api/v1/azienda/campagna/get/all/{id} | 

<a name="addcampagna"></a>
# **AddCampagna**
> string AddCampagna (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddCampagnaExample
    {
        public void main()
        {
            var apiInstance = new ControllerCampagnaApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddCampagna(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerCampagnaApi.AddCampagna: " + e.Message );
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
<a name="deletecampagna"></a>
# **DeleteCampagna**
> string DeleteCampagna (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteCampagnaExample
    {
        public void main()
        {
            var apiInstance = new ControllerCampagnaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.DeleteCampagna(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerCampagnaApi.DeleteCampagna: " + e.Message );
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
<a name="getcampagnaid"></a>
# **GetCampagnaId**
> string GetCampagnaId (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetCampagnaIdExample
    {
        public void main()
        {
            var apiInstance = new ControllerCampagnaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetCampagnaId(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerCampagnaApi.GetCampagnaId: " + e.Message );
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
<a name="getcampagneazienda"></a>
# **GetCampagneAzienda**
> string GetCampagneAzienda (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetCampagneAziendaExample
    {
        public void main()
        {
            var apiInstance = new ControllerCampagnaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetCampagneAzienda(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerCampagnaApi.GetCampagneAzienda: " + e.Message );
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
