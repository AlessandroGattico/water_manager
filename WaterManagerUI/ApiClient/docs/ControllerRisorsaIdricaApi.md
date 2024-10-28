# IO.Swagger.Api.ControllerRisorsaIdricaApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddRisorsaAzienda**](ControllerRisorsaIdricaApi.md#addrisorsaazienda) | **POST** /api/v1/risorsa/azienda/add | 
[**AddRisorsaBacino**](ControllerRisorsaIdricaApi.md#addrisorsabacino) | **POST** /api/v1/risorsa/bacino/add | 
[**DeleteRisorsaAzienda**](ControllerRisorsaIdricaApi.md#deleterisorsaazienda) | **DELETE** /api/v1/risorsa/azienda/delete/{id} | 
[**DeleteRisorsaBacino**](ControllerRisorsaIdricaApi.md#deleterisorsabacino) | **DELETE** /api/v1/risorsa/bacino/delete/{id} | 
[**GetRisorsaAziendaId**](ControllerRisorsaIdricaApi.md#getrisorsaaziendaid) | **GET** /api/v1/risorsa/azienda/get/{id} | 
[**GetRisorsaBacinoId**](ControllerRisorsaIdricaApi.md#getrisorsabacinoid) | **GET** /api/v1/risorsa/bacino/get/{id} | 
[**GetStoricoRisorseAzienda**](ControllerRisorsaIdricaApi.md#getstoricorisorseazienda) | **GET** /api/v1/risorsa/azienda/getStorico/{id} | 
[**GetStoricoRisorseBacino**](ControllerRisorsaIdricaApi.md#getstoricorisorsebacino) | **GET** /api/v1/risorsa/bacino/getStorico/{id} | 

<a name="addrisorsaazienda"></a>
# **AddRisorsaAzienda**
> string AddRisorsaAzienda (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddRisorsaAziendaExample
    {
        public void main()
        {
            var apiInstance = new ControllerRisorsaIdricaApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddRisorsaAzienda(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerRisorsaIdricaApi.AddRisorsaAzienda: " + e.Message );
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
<a name="addrisorsabacino"></a>
# **AddRisorsaBacino**
> string AddRisorsaBacino (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddRisorsaBacinoExample
    {
        public void main()
        {
            var apiInstance = new ControllerRisorsaIdricaApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddRisorsaBacino(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerRisorsaIdricaApi.AddRisorsaBacino: " + e.Message );
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
<a name="deleterisorsaazienda"></a>
# **DeleteRisorsaAzienda**
> string DeleteRisorsaAzienda (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteRisorsaAziendaExample
    {
        public void main()
        {
            var apiInstance = new ControllerRisorsaIdricaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.DeleteRisorsaAzienda(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerRisorsaIdricaApi.DeleteRisorsaAzienda: " + e.Message );
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
<a name="deleterisorsabacino"></a>
# **DeleteRisorsaBacino**
> string DeleteRisorsaBacino (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteRisorsaBacinoExample
    {
        public void main()
        {
            var apiInstance = new ControllerRisorsaIdricaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.DeleteRisorsaBacino(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerRisorsaIdricaApi.DeleteRisorsaBacino: " + e.Message );
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
<a name="getrisorsaaziendaid"></a>
# **GetRisorsaAziendaId**
> string GetRisorsaAziendaId (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetRisorsaAziendaIdExample
    {
        public void main()
        {
            var apiInstance = new ControllerRisorsaIdricaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetRisorsaAziendaId(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerRisorsaIdricaApi.GetRisorsaAziendaId: " + e.Message );
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
<a name="getrisorsabacinoid"></a>
# **GetRisorsaBacinoId**
> string GetRisorsaBacinoId (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetRisorsaBacinoIdExample
    {
        public void main()
        {
            var apiInstance = new ControllerRisorsaIdricaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetRisorsaBacinoId(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerRisorsaIdricaApi.GetRisorsaBacinoId: " + e.Message );
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
<a name="getstoricorisorseazienda"></a>
# **GetStoricoRisorseAzienda**
> string GetStoricoRisorseAzienda (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetStoricoRisorseAziendaExample
    {
        public void main()
        {
            var apiInstance = new ControllerRisorsaIdricaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetStoricoRisorseAzienda(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerRisorsaIdricaApi.GetStoricoRisorseAzienda: " + e.Message );
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
<a name="getstoricorisorsebacino"></a>
# **GetStoricoRisorseBacino**
> string GetStoricoRisorseBacino (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetStoricoRisorseBacinoExample
    {
        public void main()
        {
            var apiInstance = new ControllerRisorsaIdricaApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetStoricoRisorseBacino(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerRisorsaIdricaApi.GetStoricoRisorseBacino: " + e.Message );
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
