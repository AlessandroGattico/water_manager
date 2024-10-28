# IO.Swagger.Api.ControllerRichiestaIdricaApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddRichiesta**](ControllerRichiestaIdricaApi.md#addrichiesta) | **POST** /api/v1/richiesta/add | 
[**DeleteRichiesta**](ControllerRichiestaIdricaApi.md#deleterichiesta) | **DELETE** /api/v1/richiesta/delete/{id} | 
[**GetRichiestaId**](ControllerRichiestaIdricaApi.md#getrichiestaid) | **GET** /api/v1/richiesta/get/{id} | 
[**GetRichiesteBacino**](ControllerRichiestaIdricaApi.md#getrichiestebacino) | **GET** /api/v1/richiesta/get/allBacino/{id} | 
[**GetRichiesteColtivazione**](ControllerRichiestaIdricaApi.md#getrichiestecoltivazione) | **GET** /api/v1/richiesta/get/allColtivazione/{id} | 

<a name="addrichiesta"></a>
# **AddRichiesta**
> string AddRichiesta (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddRichiestaExample
    {
        public void main()
        {
            var apiInstance = new ControllerRichiestaIdricaApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddRichiesta(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerRichiestaIdricaApi.AddRichiesta: " + e.Message );
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
<a name="deleterichiesta"></a>
# **DeleteRichiesta**
> string DeleteRichiesta (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteRichiestaExample
    {
        public void main()
        {
            var apiInstance = new ControllerRichiestaIdricaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.DeleteRichiesta(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerRichiestaIdricaApi.DeleteRichiesta: " + e.Message );
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
<a name="getrichiestaid"></a>
# **GetRichiestaId**
> string GetRichiestaId (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetRichiestaIdExample
    {
        public void main()
        {
            var apiInstance = new ControllerRichiestaIdricaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetRichiestaId(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerRichiestaIdricaApi.GetRichiestaId: " + e.Message );
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
<a name="getrichiestebacino"></a>
# **GetRichiesteBacino**
> string GetRichiesteBacino (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetRichiesteBacinoExample
    {
        public void main()
        {
            var apiInstance = new ControllerRichiestaIdricaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetRichiesteBacino(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerRichiestaIdricaApi.GetRichiesteBacino: " + e.Message );
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
<a name="getrichiestecoltivazione"></a>
# **GetRichiesteColtivazione**
> string GetRichiesteColtivazione (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetRichiesteColtivazioneExample
    {
        public void main()
        {
            var apiInstance = new ControllerRichiestaIdricaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetRichiesteColtivazione(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerRichiestaIdricaApi.GetRichiesteColtivazione: " + e.Message );
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
