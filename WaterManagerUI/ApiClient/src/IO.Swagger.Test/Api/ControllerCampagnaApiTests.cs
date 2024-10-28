/* 
 * 
 * 
 * Generated by: https://github.com/swagger-api/swagger-codegen.git
 */
using System;
using System.IO;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Reflection;
using RestSharp;
using NUnit.Framework;

using IO.Swagger.Client;
using IO.Swagger.Api;

namespace IO.Swagger.Test
{
    /// <summary>
    ///  Class for testing ControllerCampagnaApi
    /// </summary>
    /// <remarks>
    /// This file is automatically generated by Swagger Codegen.
    /// Please update the test case below to test the API endpoint.
    /// </remarks>
    [TestFixture]
    public class ControllerCampagnaApiTests
    {
        private ControllerCampagnaApi instance;

        /// <summary>
        /// Setup before each unit test
        /// </summary>
        [SetUp]
        public void Init()
        {
            instance = new ControllerCampagnaApi();
        }

        /// <summary>
        /// Clean up after each unit test
        /// </summary>
        [TearDown]
        public void Cleanup()
        {

        }

        /// <summary>
        /// Test an instance of ControllerCampagnaApi
        /// </summary>
        [Test]
        public void InstanceTest()
        {
            // TODO uncomment below to test 'IsInstanceOfType' ControllerCampagnaApi
            //Assert.IsInstanceOfType(typeof(ControllerCampagnaApi), instance, "instance is a ControllerCampagnaApi");
        }

        /// <summary>
        /// Test AddCampagna
        /// </summary>
        [Test]
        public void AddCampagnaTest()
        {
            // TODO uncomment below to test the method and replace null with proper value
            //string body = null;
            //var response = instance.AddCampagna(body);
            //Assert.IsInstanceOf<string> (response, "response is string");
        }
        /// <summary>
        /// Test DeleteCampagna
        /// </summary>
        [Test]
        public void DeleteCampagnaTest()
        {
            // TODO uncomment below to test the method and replace null with proper value
            //int? id = null;
            //var response = instance.DeleteCampagna(id);
            //Assert.IsInstanceOf<string> (response, "response is string");
        }
        /// <summary>
        /// Test GetCampagnaId
        /// </summary>
        [Test]
        public void GetCampagnaIdTest()
        {
            // TODO uncomment below to test the method and replace null with proper value
            //int? id = null;
            //var response = instance.GetCampagnaId(id);
            //Assert.IsInstanceOf<string> (response, "response is string");
        }
        /// <summary>
        /// Test GetCampagneAzienda
        /// </summary>
        [Test]
        public void GetCampagneAziendaTest()
        {
            // TODO uncomment below to test the method and replace null with proper value
            //int? id = null;
            //var response = instance.GetCampagneAzienda(id);
            //Assert.IsInstanceOf<string> (response, "response is string");
        }
    }

}