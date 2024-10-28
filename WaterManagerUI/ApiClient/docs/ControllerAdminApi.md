# IO.Swagger.Api.ControllerAdminApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**AddEsigenza**](ControllerAdminApi.md#addesigenza) | **POST** /api/v1/admin/esigenza/add | 
[**AddIrrigazione**](ControllerAdminApi.md#addirrigazione) | **POST** /api/v1/admin/irrigazione/add | 
[**AddRaccolto**](ControllerAdminApi.md#addraccolto) | **POST** /api/v1/admin/raccolto/add | 
[**AddSensorType**](ControllerAdminApi.md#addsensortype) | **DELETE** /api/v1/admin/sensor/add | 
[**DeleteEsigenza**](ControllerAdminApi.md#deleteesigenza) | **DELETE** /api/v1/admin/esigenza/delete/{param} | 
[**DeleteIrrigazione**](ControllerAdminApi.md#deleteirrigazione) | **DELETE** /api/v1/admin/irrigazione/delete/{param} | 
[**DeleteRaccolto**](ControllerAdminApi.md#deleteraccolto) | **DELETE** /api/v1/admin/raccolto/delete/{param} | 
[**DeleteSensorType**](ControllerAdminApi.md#deletesensortype) | **DELETE** /api/v1/admin/sensor/delete/{param} | 
[**GetAdmin**](ControllerAdminApi.md#getadmin) | **GET** /api/v1/admin/get/{id} | 
[**GetAllAziende**](ControllerAdminApi.md#getallaziende) | **GET** /api/v1/admin/aziende/get/all | 
[**GetAllCampagne**](ControllerAdminApi.md#getallcampagne) | **GET** /api/v1/admin/campagne/get/all | 
[**GetAllCampi**](ControllerAdminApi.md#getallcampi) | **GET** /api/v1/admin/campi/get/all | 
[**GetBacini1**](ControllerAdminApi.md#getbacini1) | **GET** /api/v1/admin/bacino/get/all | 
[**GetCount**](ControllerAdminApi.md#getcount) | **GET** /api/v1/admin/count | 
[**GetGestoriBacino**](ControllerAdminApi.md#getgestoribacino) | **GET** /api/v1/admin/gi/get/all | 
[**GetUsers**](ControllerAdminApi.md#getusers) | **GET** /api/v1/admin/users/get/all | 
[**GetgestoriAzienda**](ControllerAdminApi.md#getgestoriazienda) | **GET** /api/v1/admin/ga/get/all | 

<a name="addesigenza"></a>
# **AddEsigenza**
> string AddEsigenza (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddEsigenzaExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddEsigenza(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.AddEsigenza: " + e.Message );
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
<a name="addirrigazione"></a>
# **AddIrrigazione**
> string AddIrrigazione (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddIrrigazioneExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddIrrigazione(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.AddIrrigazione: " + e.Message );
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
<a name="addraccolto"></a>
# **AddRaccolto**
> string AddRaccolto (string body)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddRaccoltoExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();
            var body = new string(); // string | 

            try
            {
                string result = apiInstance.AddRaccolto(body);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.AddRaccolto: " + e.Message );
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
<a name="addsensortype"></a>
# **AddSensorType**
> string AddSensorType ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class AddSensorTypeExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();

            try
            {
                string result = apiInstance.AddSensorType();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.AddSensorType: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="deleteesigenza"></a>
# **DeleteEsigenza**
> string DeleteEsigenza (string param)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteEsigenzaExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();
            var param = param_example;  // string | 

            try
            {
                string result = apiInstance.DeleteEsigenza(param);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.DeleteEsigenza: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **param** | **string**|  | 

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="deleteirrigazione"></a>
# **DeleteIrrigazione**
> string DeleteIrrigazione (string param)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteIrrigazioneExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();
            var param = param_example;  // string | 

            try
            {
                string result = apiInstance.DeleteIrrigazione(param);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.DeleteIrrigazione: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **param** | **string**|  | 

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="deleteraccolto"></a>
# **DeleteRaccolto**
> string DeleteRaccolto (string param)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteRaccoltoExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();
            var param = param_example;  // string | 

            try
            {
                string result = apiInstance.DeleteRaccolto(param);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.DeleteRaccolto: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **param** | **string**|  | 

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="deletesensortype"></a>
# **DeleteSensorType**
> string DeleteSensorType (string param)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class DeleteSensorTypeExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();
            var param = param_example;  // string | 

            try
            {
                string result = apiInstance.DeleteSensorType(param);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.DeleteSensorType: " + e.Message );
            }
        }
    }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **param** | **string**|  | 

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getadmin"></a>
# **GetAdmin**
> string GetAdmin (int? id)



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetAdminExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();
            var id = 56;  // int? | 

            try
            {
                string result = apiInstance.GetAdmin(id);
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.GetAdmin: " + e.Message );
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
<a name="getallaziende"></a>
# **GetAllAziende**
> string GetAllAziende ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetAllAziendeExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();

            try
            {
                string result = apiInstance.GetAllAziende();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.GetAllAziende: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getallcampagne"></a>
# **GetAllCampagne**
> string GetAllCampagne ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetAllCampagneExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();

            try
            {
                string result = apiInstance.GetAllCampagne();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.GetAllCampagne: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getallcampi"></a>
# **GetAllCampi**
> string GetAllCampi ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetAllCampiExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();

            try
            {
                string result = apiInstance.GetAllCampi();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.GetAllCampi: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getbacini1"></a>
# **GetBacini1**
> string GetBacini1 ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetBacini1Example
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();

            try
            {
                string result = apiInstance.GetBacini1();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.GetBacini1: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getcount"></a>
# **GetCount**
> string GetCount ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetCountExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();

            try
            {
                string result = apiInstance.GetCount();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.GetCount: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getgestoribacino"></a>
# **GetGestoriBacino**
> string GetGestoriBacino ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetGestoriBacinoExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();

            try
            {
                string result = apiInstance.GetGestoriBacino();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.GetGestoriBacino: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getusers"></a>
# **GetUsers**
> string GetUsers ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetUsersExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();

            try
            {
                string result = apiInstance.GetUsers();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.GetUsers: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
<a name="getgestoriazienda"></a>
# **GetgestoriAzienda**
> string GetgestoriAzienda ()



### Example
```csharp
using System;
using System.Diagnostics;
using IO.Swagger.Api;
using IO.Swagger.Client;
using IO.Swagger.Model;

namespace Example
{
    public class GetgestoriAziendaExample
    {
        public void main()
        {
            var apiInstance = new ControllerAdminApi();

            try
            {
                string result = apiInstance.GetgestoriAzienda();
                Debug.WriteLine(result);
            }
            catch (Exception e)
            {
                Debug.Print("Exception when calling ControllerAdminApi.GetgestoriAzienda: " + e.Message );
            }
        }
    }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
