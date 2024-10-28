# IO.Swagger.Api.ControllerCampoApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddCampo**](ControllerCampoApi.md#addcampo) | **POST** /api/v1/azienda/campo/add | 
[**DeleteCampo**](ControllerCampoApi.md#deletecampo) | **DELETE** /api/v1/azienda/campo/delete/{id} | 
[**GetCampiCampagna**](ControllerCampoApi.md#getcampicampagna) | **GET** /api/v1/azienda/campo/get/all/{id} | 
[**GetCampoId**](ControllerCampoApi.md#getcampoid) | **GET** /api/v1/azienda/campo/get/{id} | 

<a name="addcampo"></a>
# **AddCampo**
> string AddCampo (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddCampoExample
    {
        public void main()
        {
            var apiInstance = new ControllerCampoApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddCampo(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerCampoApi.AddCampo: " + e.Message );
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
<a name="deletecampo"></a>
# **DeleteCampo**
> string DeleteCampo (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteCampoExample
    {
        public void main()
        {
            var apiInstance = new ControllerCampoApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.DeleteCampo(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerCampoApi.DeleteCampo: " + e.Message );
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
<a name="getcampicampagna"></a>
# **GetCampiCampagna**
> string GetCampiCampagna (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetCampiCampagnaExample
    {
        public void main()
        {
            var apiInstance = new ControllerCampoApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetCampiCampagna(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerCampoApi.GetCampiCampagna: " + e.Message );
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
<a name="getcampoid"></a>
# **GetCampoId**
> string GetCampoId (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetCampoIdExample
    {
        public void main()
        {
            var apiInstance = new ControllerCampoApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetCampoId(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerCampoApi.GetCampoId: " + e.Message );
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
