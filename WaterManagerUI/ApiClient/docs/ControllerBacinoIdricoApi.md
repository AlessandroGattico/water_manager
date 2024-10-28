# IO.Swagger.Api.ControllerBacinoIdricoApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddBacino**](ControllerBacinoIdricoApi.md#addbacino) | **POST** /api/v1/bacino/add | 
[**DeleteBacino**](ControllerBacinoIdricoApi.md#deletebacino) | **DELETE** /api/v1/bacino/delete/{id} | 
[**GetBacinoGi**](ControllerBacinoIdricoApi.md#getbacinogi) | **GET** /api/v1/bacino/get/gestore/{id} | 
[**GetBacinoId**](ControllerBacinoIdricoApi.md#getbacinoid) | **GET** /api/v1/bacino/get/{id} | 

<a name="addbacino"></a>
# **AddBacino**
> string AddBacino (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddBacinoExample
    {
        public void main()
        {
            var apiInstance = new ControllerBacinoIdricoApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddBacino(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerBacinoIdricoApi.AddBacino: " + e.Message );
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
<a name="deletebacino"></a>
# **DeleteBacino**
> string DeleteBacino (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteBacinoExample
    {
        public void main()
        {
            var apiInstance = new ControllerBacinoIdricoApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.DeleteBacino(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerBacinoIdricoApi.DeleteBacino: " + e.Message );
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
<a name="getbacinogi"></a>
# **GetBacinoGi**
> string GetBacinoGi (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetBacinoGiExample
    {
        public void main()
        {
            var apiInstance = new ControllerBacinoIdricoApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetBacinoGi(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerBacinoIdricoApi.GetBacinoGi: " + e.Message );
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
<a name="getbacinoid"></a>
# **GetBacinoId**
> string GetBacinoId (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetBacinoIdExample
    {
        public void main()
        {
            var apiInstance = new ControllerBacinoIdricoApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetBacinoId(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerBacinoIdricoApi.GetBacinoId: " + e.Message );
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
